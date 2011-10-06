package com.burse.client.ui.feed;


import com.burse.client.css.Resources;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class FeedCell extends AbstractCell<FeedDto> {

    @Override
    public void render(Context context, FeedDto value, SafeHtmlBuilder sb) {
        if (value == null) {
            return;
        }

        sb.appendHtmlConstant("<div class=\"" + Resources.cellWidgetResources().cellListStyle().feedCell() + "\">");

        sb.appendHtmlConstant("<div class=\"" + Resources.cellWidgetResources().cellListStyle().feedCellTitle() + "\">");
        sb.appendEscaped(value.getTitle());
        sb.appendHtmlConstant("</div>");

        sb.appendHtmlConstant("<div class=\"" + Resources.cellWidgetResources().cellListStyle().feedCellType() + "\">");
        sb.appendEscaped(".");

        sb.appendHtmlConstant("<div class=\"" + Resources.cellWidgetResources().cellListStyle().feedCellSize() + "\">");
        sb.appendEscaped(".");
        sb.appendHtmlConstant("</div>");
        sb.appendHtmlConstant("</div>");

        sb.appendHtmlConstant("</div>");
    }
}
