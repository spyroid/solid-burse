package com.burse.client.ui.feed;

import com.burse.shared.FeedDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.Widget;

public class FeedCell extends Widget {

	public interface Resources extends ClientBundle {
		@Source(Style.DEFAULT_CSS)
		Style feedListStyle();
	}

	public interface Style extends CssResource {
		String DEFAULT_CSS = "com/burse/client/res/FeedCell.css";

		String feedCell();
		String firstColumn();
		String secondColumn();
		String thirdColumn();
		String starButton();
		String active();
		String inactive();
		String selected();
	}

	private static Resources DEFAULT_RESOURCES;
	public static Resources getDefaultResources() {
		if (DEFAULT_RESOURCES == null) {
			DEFAULT_RESOURCES = GWT.create(Resources.class);
		}
		return DEFAULT_RESOURCES;
	}

	
	private Style style = getDefaultResources().feedListStyle();
	private FeedDto dto;
	private DivElement root = Document.get().createDivElement();
	private DivElement first = Document.get().createDivElement();
	private DivElement star = Document.get().createDivElement();

	private DivElement second = Document.get().createDivElement();
	private DivElement title = Document.get().createDivElement();
	private DivElement company = Document.get().createDivElement();
	
	private DivElement third = Document.get().createDivElement();

	public FeedCell(FeedDto _dto) {
		dto = _dto;
		root.addClassName(style.feedCell());
		root.setAttribute("idx", String.valueOf(_dto.getId()));
		setElement(root);

		root.appendChild(first);
		first.addClassName(style.firstColumn());
		first.appendChild(star);
		star.addClassName(style.starButton());
		star.addClassName(_dto.isFavorite() ? style.active() : style.inactive());
		
		second.appendChild(title);
		second.addClassName(style.secondColumn());
		title.setInnerHTML(dto.getTitle());
		second.appendChild(company);
		company.setInnerHTML("AAA sro from Prague, CZ");
		root.appendChild(second);
		
		root.appendChild(third);
		third.addClassName(style.thirdColumn());
		third.setInnerHTML("<b>71 000&euro;</b><br/>100pct");
		
		style.ensureInjected();

	}

	public void toggleStar() {
		star.removeClassName(dto.isFavorite() ? style.active() : style.inactive());
		star.addClassName(!dto.isFavorite() ? style.active() : style.inactive());
		dto.setFavorite(!dto.isFavorite());
	}

	public void deselect() {
		root.removeClassName(style.selected());
	}

	public void select() {
		root.addClassName(style.selected());
	}

	public FeedDto getDto() {
		return dto;
	}
	
	/*
	 * @Override public void render(Context context, FeedDto value,
	 * SafeHtmlBuilder sb) { if (value == null) { return; }
	 * 
	 * sb.appendHtmlConstant("<div class=\"" +
	 * Resources.cellWidgetResources().cellListStyle().feedCell() + "\">");
	 * 
	 * sb.appendHtmlConstant("<div class=\"" +
	 * Resources.cellWidgetResources().cellListStyle().feedCellTitle() + "\">");
	 * sb.appendEscaped(value.getTitle()); sb.appendHtmlConstant("</div>");
	 * 
	 * sb.appendHtmlConstant("<div class=\"" +
	 * Resources.cellWidgetResources().cellListStyle().feedCellType() + "\">");
	 * sb.appendEscaped(".");
	 * 
	 * sb.appendHtmlConstant("<div class=\"" +
	 * Resources.cellWidgetResources().cellListStyle().feedCellSize() + "\">");
	 * sb.appendEscaped("."); sb.appendHtmlConstant("</div>");
	 * sb.appendHtmlConstant("</div>");
	 * 
	 * sb.appendHtmlConstant("</div>"); }
	 */
}
