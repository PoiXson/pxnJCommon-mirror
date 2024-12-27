/*
package com.poixson.swing;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JTextField;


public class JTextFieldGhostable extends JTextField implements FocusListener {
	private static final long serialVersionUID = 1L;

	protected final AtomicBoolean ghosting = new AtomicBoolean(false);
	protected final AtomicReference<String> ghostText = new AtomicReference<String>(null);

	protected final Color defaultForeground;



	public JTextFieldGhostable(final String ghostText) {
		this(ghostText, null);
	}
	public JTextFieldGhostable(final String ghostText, final String text) {
		super(text);
		this.addFocusListener(this);
		this.defaultForeground = this.getForeground();
		this.ghostText.set( IfEmpty(ghostText, "") );
		if (IsEmpty(text)) {
			this.ghosting.set(true);
			this.update();
		} else {
			this.ghosting.set(false);
		}
	}



	// -------------------------------------------------------------------------------
	// state



	public void setGhosting(final boolean value) {
		final boolean previous = this.ghosting.getAndSet(value);
		if (previous != value)
			this.update();
	}
	public void update() {
		if (this.ghosting.get()) {
			super.setText(this.ghostText.get());
			this.setHorizontalAlignment(JTextField.CENTER);
			this.setForeground(Color.GRAY);
		} else {
			if (MatchString(this.ghostText.get(), this.getText()))
				super.setText("");
			this.setHorizontalAlignment(JTextField.LEFT);
			this.setForeground(this.defaultForeground);
		}
	}



	@Override
	public void setText(final String text) {
		if (IsEmpty(text)) {
			this.setGhosting(true);
		} else {
			this.setGhosting(false);
			super.setText(text);
		}
	}



	public void setGhostText(final String ghostText) {
		this.ghostText.set(ghostText);
		if (this.ghosting.get())
			this.update();
	}



	public boolean isGhosting() {
		return this.ghosting.get();
	}



	// -------------------------------------------------------------------------------
	// focus listener



	@Override
	public void focusGained(final FocusEvent event) {
		this.setGhosting(false);
	}
	@Override
	public void focusLost(final FocusEvent event) {
		if (IsEmpty(super.getText())) this.setGhosting(true);
		else                          this.update();
	}



}
*/
