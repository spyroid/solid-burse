package com.burse.client.ui.feed;

import com.burse.shared.FeedDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.dom.client.TableElement;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.dom.client.TableSectionElement;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class FeedInfo extends ScrollPanel {
    public interface Resources extends ClientBundle {
        @Source(Style.DEFAULT_CSS)
        Style feedInfoStyle();
    }

    public interface Style extends CssResource {
        String DEFAULT_CSS = "com/burse/client/res/FeedInfo.css";

        String feedInfo();

        String title();

        String dataBlock();
    }

    private static Resources DEFAULT_RESOURCES;

    public static Resources getDefaultResources() {
        if (DEFAULT_RESOURCES == null) {
            DEFAULT_RESOURCES = GWT.create(Resources.class);
        }
        return DEFAULT_RESOURCES;
    }

    private Style style = getDefaultResources().feedInfoStyle();

    public FeedInfo() {
        setStyleName(style.feedInfo());
        style.ensureInjected();
    }

    public void show(FeedDto dto) {
        clear();
        add(new FeedInfoPanel(dto));
    }

    private class FeedInfoPanel extends Widget {
        public FeedInfoPanel(FeedDto dto) {
            DivElement container = Document.get().createDivElement();
            container.appendChild(getFeedTitle(dto));
            container.appendChild(getOfferDetails(dto));
            container.appendChild(getContactDetails(dto));
            container.appendChild(getComments(dto));
            setElement(container);
        }

        private Node getComments(FeedDto dto) {
            DivElement offDetails = Document.get().createDivElement();
            offDetails.addClassName(style.dataBlock());
            DivElement t = Document.get().createDivElement();
            t.addClassName(style.title());
            t.setInnerHTML("Comments:");
            offDetails.appendChild(t);
            DivElement c = Document.get().createDivElement();
            c.setInnerHTML("Black, EuroSpec, EN Keyb");
            offDetails.appendChild(c);
            return offDetails;
        }

        private Node getOfferDetails(FeedDto dto) {
            DivElement offDetails = Document.get().createDivElement();
            offDetails.addClassName(style.dataBlock());
            DivElement t = Document.get().createDivElement();
            t.addClassName(style.title());
            t.setInnerHTML("Offer details:");
            offDetails.appendChild(t);
            String[][] data = new String[][] {
                    {"Posted at","17:21 GMT, Oct 09, 2011"},
                    {"Manufacturer","Apple"},
                    {"Model","2001"},
                    {"Price","Negotiable"},
                    {"Quantity","300"},
                    {"Condition","New"},
                    {"Specification","Euro spec"},
                    {"Network stock","No"},
                    };
            offDetails.appendChild(getData(data));
            return offDetails;
        }

        private Node getContactDetails(FeedDto dto) {
            DivElement offDetails = Document.get().createDivElement();
            offDetails.addClassName(style.dataBlock());
            DivElement t = Document.get().createDivElement();
            t.addClassName(style.title());
            t.setInnerHTML("Contact details:");
            offDetails.appendChild(t);
            String[][] data = new String[][] {
                    {"Company","MAPLES Trading LLC"},
                    {"Contact person","Nikhil Raja"},
                    {"Country","UAE"},
                    {"E-mail","nikhil@maples.ae"},
                    {"GSM phone","+971-50-6519216"},
                    {"Land phone","+971-4-2299560"},
                    {"Alternative phone","Euro spec"},
                    {"Additional details","No"},
                    };
            offDetails.appendChild(getData(data));
            return offDetails;
        }

        private Node getData(String[][] data) {
            TableElement table = Document.get().createTableElement();
            TableSectionElement body = Document.get().createTBodyElement();
            table.appendChild(body);
            for (String[] r: data) {
                TableRowElement row = Document.get().createTRElement();
                body.appendChild(row);
                TableCellElement td1 = Document.get().createTDElement();
                td1.setInnerHTML(r[0] + ":");
                row.appendChild(td1);
                TableCellElement td2 = Document.get().createTDElement();
                td2.setInnerHTML(r[1]);
                row.appendChild(td2);
            }
            return table;
        }

        private Node getFeedTitle(FeedDto dto) {
            DivElement title = Document.get().createDivElement();
            title.addClassName(style.title());
            title.setInnerHTML("Trade #" + dto.getId() + ": " + dto.getTitle());
            return title;
        }
    }
}
