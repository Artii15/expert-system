package com.sample;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;

public class NoneSelectedButtonGroup extends ButtonGroup {

	private static final long serialVersionUID = 1L;

	@Override
	  public void setSelected(ButtonModel model, boolean selected) {

		  if (selected) {
			  super.setSelected(model, selected);
		  } 
		  else {
			  clearSelection();
		  }
	  }
}
