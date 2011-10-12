package com.burse.client.css;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.resources.client.CssResource.NotStrict;
import com.google.gwt.user.cellview.client.CellList;

public class Resources {

	public interface CellWidgetStyles extends CellList.Style {
		String feedCell();

		String feedCellTitle();

		String feedCellType();

		String feedCellSize();
	}

	public interface Common extends CssResource {
		String appHeader();

		String appMenu();

		String appMenuItem();

		String appTitle();

		String headerLink();

		String buttonsHeader();

		String mainArea();

		String searchField();

		String searchFieldContainer();
	}

	public interface CellWidgetResources extends CellList.Resources {
		@Source("com/burse/client/res/FeedCell.css")
		CellWidgetStyles cellListStyle();
	}

	public interface Bundle extends ClientBundle {
		@NotStrict
		@Source("com/burse/client/res/common.css")
		Common common();

		CellWidgetResources cellWidgetResources();

		@Source("com/burse/client/res/main-area-bg.png")
		DataResource mainAreaBg();

		@Source("com/burse/client/res/cat.html")
		TextResource catalogPlaceHolder();
	}

	public static Bundle bundle;

	static {
		bundle = GWT.create(Bundle.class);
		bundle.common().ensureInjected();
	}

	public static Common common() {
		return bundle.common();
	}

	public static CellWidgetResources cellWidgetResources() {
		return bundle.cellWidgetResources();
	}

	public static Bundle bundle() {
		return bundle;
	}
}
