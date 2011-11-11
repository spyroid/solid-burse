package com.burse.client.ui.catalog;

import com.burse.client.ClientFactory;
import com.burse.client.css.Resources;
import com.burse.shared.CatalogServiceAsync;
import com.burse.shared.ProductDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
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

	private HandlerRegistration addValueChangeHandler;

	public TopCatalogView() {
		initWidget(root = uiBinder.createAndBindUi(this));
		EmptyTextBoxMaskProvider.register("Type to Search", searchField);

		showDefaultPanel();

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
		if ("catalog:search".equals(current) == false) {
			History.newItem("catalog:search");
		}
		CatalogServiceAsync greetingService = factory.greetingService();
		greetingService.queryProducts(searchField.getText(), new AsyncCallback<ProductDto[]>() {

			@Override
			public void onSuccess(ProductDto[] result) {
				HTML html = new HTML();

				for (ProductDto string : result) {
					Element productElement = DOM.createAnchor();
					productElement.setAttribute("href", "#catalog:product");

					productElement.setInnerText(string.name);
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
		dto.name = "MacBookPro";
		dto.manufacturer = "Apple";
		dto.avgPrice = "2000";
		dto.sell = 100;
		dto.buy = 20;
		dto.image = "/dimages/MacBookPro.png";

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

	@Override
	protected void onAttach() {
		super.onAttach();
		History.newItem("catalog", true);
		addValueChangeHandler = History.addValueChangeHandler(this);

	}

	@Override
	protected void onDetach() {
		super.onDetach();
		addValueChangeHandler.removeHandler();
	}

}
