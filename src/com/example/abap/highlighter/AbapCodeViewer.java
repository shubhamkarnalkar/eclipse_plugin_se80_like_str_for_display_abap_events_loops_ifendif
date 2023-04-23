package com.example.abap.highlighter;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.ui.editors.*;
import org.eclipse.ui.part.*;


public class AbapCodeViewer extends ViewPart {
    public static final String ID = "com.example.abap.highlighter.AbapCodeViewer";
    private TreeViewer viewer;

    public void createPartControl(Composite parent) {
        viewer = new TreeViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
        viewer.setContentProvider(new AbapCodeContentProvider());
        viewer.setLabelProvider(new AbapCodeLabelProvider());
        viewer.setInput("root");
//        viewer.addDoubleClickListener(new AbapCodeDoubleClickListener());
    }

    public void setFocus() {
        viewer.getControl().setFocus();
    }

    private class AbapCodeContentProvider implements ITreeContentProvider {
        @Override
        public Object[] getElements(Object inputElement) {
            return ((AbapCodeNode) inputElement).getChildren().toArray();
        }

        @Override
        public Object[] getChildren(Object parentElement) {
            return ((AbapCodeNode) parentElement).getChildren().toArray();
        }

        @Override
        public Object getParent(Object element) {
            return null;
        }

        @Override
        public boolean hasChildren(Object element) {
            return ((AbapCodeNode) element).hasChildren();
        }
    }

    private class AbapCodeLabelProvider extends LabelProvider {
        @Override
        public String getText(Object element) {
            return ((AbapCodeNode) element).getLabel();
        }
    }

    private class AbapCodeDoubleClickListener implements IDoubleClickListener {
        @Override
        public void doubleClick(DoubleClickEvent event) {
            IStructuredSelection selection = (IStructuredSelection) event.getSelection();
            AbapCodeNode node = (AbapCodeNode) selection.getFirstElement();

            if (node != null) {
                int offset = node.getOffset();
                int length = node.getLength();

                IEditorPart editor = getSite().getPage().getActiveEditor();
                if (editor instanceof AbstractTextEditor) {
                    AbstractTextEditor textEditor = (AbstractTextEditor) editor;
                    textEditor.selectAndReveal(offset, length);
                }
            }
        }
    }
}
