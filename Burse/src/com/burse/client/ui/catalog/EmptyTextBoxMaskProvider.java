package com.burse.client.ui.catalog;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.TextBox;

public class EmptyTextBoxMaskProvider implements FocusHandler, BlurHandler {
	TextBox box;

	String emptyText;

	boolean hasValue = false;

	List<HandlerRegistration> handlers;

	private EmptyTextBoxMaskProvider(String emptyText, TextBox box) {
		this.emptyText = emptyText;
		this.box = box;
		handlers = Arrays.asList(box.addBlurHandler(this), box.addFocusHandler(this));
		hasValue = false;
		box.setText(emptyText);

	}

	public static EmptyTextBoxMaskProvider register(String emptyText, TextBox box) {
		return new EmptyTextBoxMaskProvider(emptyText, box);
	}

	@Override
	public void onFocus(FocusEvent event) {
		GWT.log("focus request");
		if (hasValue == false) {
			box.setText("");
			hasValue = true;
		}

	}

	@Override
	public void onBlur(BlurEvent event) {
		GWT.log("blur request");
		if (box.getText().isEmpty()) {
			box.setText(emptyText);
			hasValue = false;
		}
	}

	public void dispose() {
		for (HandlerRegistration reg : handlers) {
			reg.removeHandler();
		}
		if (hasValue) {
			box.setText("");
		}
	}

}
