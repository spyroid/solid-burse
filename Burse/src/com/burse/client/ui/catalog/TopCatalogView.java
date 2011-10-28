package com.burse.client.ui.catalog;

import com.burse.client.ClientFactory;
import com.burse.client.css.Resources;
import com.burse.shared.GreetingServiceAsync;
import com.burse.shared.ProductDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class TopCatalogView extends Composite implements ValueChangeHandler<String> {

	ClientFactory factory = GWT.create(ClientFactory.class);

	private String current = "";

	private Widget currentView;

	interface CatalogViewBinder extends UiBinder<HTMLPanel, TopCatalogView> {
	}

	private static CatalogViewBinder uiBinder = GWT.create(CatalogViewBinder.class);
	protected HTMLPanel root;

	@UiField
	protected TextBox searchField;

	@UiField
	protected DivElement displayDiv;

	@UiField
	protected Anchor browseAnchor;

	protected ProductBrowserView browserView;
	protected ProductDetailsView productDetailsView;

	public TopCatalogView() {
		initWidget(root = uiBinder.createAndBindUi(this));
		EmptyTextBoxMaskProvider.register("Type to Search", searchField);

		showDefaultPanel();
		History.addValueChangeHandler(this);
	}

	@UiHandler("searchField")
	protected void doSearch(KeyUpEvent event) {
		if (searchField.getText().isEmpty() == false) {
			tryStartSearch();
		} else {
			showDefaultPanel();
		}
	}

	// @UiHandler("browseAnchor")
	protected void tryShowBrowse(ClickEvent event) {
		if (browserView == null) {
			browserView = new ProductBrowserView();
		}
		setDisplayView(browserView);

	}

	protected void showDefaultPanel() {
		HTML html = new HTML();
		String text = Resources.bundle.catalogPlaceHolder().getText();
		html.setHTML(text);
		setDisplayView(html);
		

	}

	protected void tryStartSearch() {
		setDisplayView(new HTML("Searching"));
		
		GreetingServiceAsync greetingService = factory.greetingService();
		greetingService.queryProducts(searchField.getText(), new AsyncCallback<String[]>() {

			@Override
			public void onSuccess(String[] result) {
				HTML html = new HTML();
				
				for (String string : result) {
					Element productElement = DOM.createAnchor();
					productElement.setAttribute("href", "#catalog:product");
					
					
					productElement.setInnerText(string);
					html.getElement().appendChild(productElement);
					html.getElement().appendChild(DOM.createElement("br"));
				}
				setDisplayView(html);

			}

			@Override
			public void onFailure(Throwable caught) {
				setDisplayView(new HTML("Nothing found"));

			}
		});

	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String value = event.getValue();
		if (value != null && value.isEmpty() == false && value.startsWith("catalog:")) {
			String substring = value.substring("catalog:".length());
			GWT.log("Navigating into " + substring);
			if (current.equals(substring)) {
				return;
			}

			if (substring.startsWith("product")) {
				tryShowProduct(substring);
			} else if (substring.startsWith("browse")) {
				tryShowBrowse(null);
			}
			current = substring;
		}

	}

	private void tryShowProduct(String query) {
		if (productDetailsView == null) {
			productDetailsView = new ProductDetailsView(getProductFrom(query));
		}
		setDisplayView(productDetailsView);
		
	}

	private ProductDto getProductFrom(String query) {
		ProductDto dto = new ProductDto();
		dto.id = "appleMacBookPro";
		dto.name = "Apple MacBook Pro 17";
		dto.avgPrice = "2000";
		dto.sell = 100;
		dto.buy = 20;
		dto.image = "http://www.gadgetcage.com/wp-content/uploads/2010/05/apple_17-inch_macbook_pro-480x301.jpg";
		return dto;
	}

	protected void setDisplayView(Widget widget) {
		if (currentView == widget) {
			return;
		}
		if (currentView != null) {
			root.remove(currentView);
		}
		root.add(widget);
		currentView = widget;
	}

}
