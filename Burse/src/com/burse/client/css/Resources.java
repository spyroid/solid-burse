package com.burse.client.css;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;
import com.google.gwt.resources.client.DataResource;
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
    }

    public interface CellWidgetResources extends CellList.Resources {
        @Source("FeedCell.css")
        CellWidgetStyles cellListStyle();
    }

    public interface Bundle extends ClientBundle {
        @NotStrict
        @Source("common.css")
        Common common();
        CellWidgetResources cellWidgetResources();
        
        @Source("com/burse/client/css/main-area-bg.png")
        DataResource mainAreaBg();
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
}
