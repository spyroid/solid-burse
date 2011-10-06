package com.burse.client.ui.feed;

import java.util.ArrayList;

import com.burse.client.css.Resources;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.cellview.client.AbstractPager;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.HasRows;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;

public class FeedList extends AbstractPager {

    private static final int DEFAULT_INCREMENT = 20;
    private int incrementSize = DEFAULT_INCREMENT;
    private int lastScrollPos = 0;
    private final SingleSelectionModel<FeedDto> listSelectionModel = new SingleSelectionModel<FeedDto>(FeedDto.KEY_PROVIDER);
    private ListDataProvider<FeedDto> dataProvider = new ListDataProvider<FeedDto>();
    private CellList<FeedDto> cellList = null;

    /**
     * The scrollable panel.
     */
    private final ScrollPanel scrollable = new ScrollPanel();
    private HandlerRegistration selectionHandler = null;

    /**
     * Construct a new {@link FlexibleSnippetsPanel}.
     */
    public FeedList() {
        initWidget(scrollable);

        FeedCell feedCell = new FeedCell();
        cellList = new CellList<FeedDto>(feedCell, Resources.cellWidgetResources(), FeedDto.KEY_PROVIDER);
        cellList.setPageSize(30);

        // Add a selection model so we can select cells.
        cellList.setSelectionModel(listSelectionModel);

        dataProvider.addDataDisplay(cellList);
        setDisplay(cellList);
        
        
        // Do not let the scrollable take tab focus.
        scrollable.getElement().setTabIndex(-1);

        // Handle scroll events.
        scrollable.addScrollHandler(new ScrollHandler() {
            public void onScroll(ScrollEvent event) {
                // If scrolling up, ignore the event.
                int oldScrollPos = lastScrollPos;
                lastScrollPos = scrollable.getVerticalScrollPosition();
                if (oldScrollPos >= lastScrollPos) {
                    return;
                }

                HasRows display = getDisplay();
                if (display == null) {
                    return;
                }
                int maxScrollTop = scrollable.getWidget().getOffsetHeight() - scrollable.getOffsetHeight();
                if (lastScrollPos >= maxScrollTop) {
                    // We are near the end, so increase the page size.
                    int newPageSize = Math.min(display.getVisibleRange().getLength() + incrementSize, display.getRowCount());
                    display.setVisibleRange(0, newPageSize);
                }
            }
        });
    }

    /**
     * Get the number of rows by which the range is increased when the scrollbar
     * reaches the bottom.
     * 
     * @return the increment size
     */
    public int getIncrementSize() {
        return incrementSize;
    }

    @Override
    public void setDisplay(HasRows display) {
        assert display instanceof Widget : "display must extend Widget";
        scrollable.setWidget((Widget) display);
        super.setDisplay(display);
    }

    public void setIncrementSize(int incrementSize) {
        this.incrementSize = incrementSize;
    }

    @Override
    protected void onRangeOrRowCountChanged() {
    }
    
    public void updateSnippetsList(final ArrayList<FeedDto> result) {
        deselectAll();
        dataProvider.getList().clear();
        dataProvider.getList().addAll(result);
    }

    
    public void addSelectionHandler(Handler handler) {
        if (selectionHandler != null) {
            selectionHandler.removeHandler();
        }
        selectionHandler = listSelectionModel.addSelectionChangeHandler(handler);
    }
    
    public boolean selectItem(int id) {
        FeedDto dto = null;
        for (int i = 0; i < dataProvider.getList().size(); i++) {
            FeedDto t = dataProvider.getList().get(i);
            if (t != null && t.getId() == id) {
                dto = t;
                break;
            }
        }
        if (dto != null) {
            listSelectionModel.setSelected(dto, true);
        }
        return dto != null;
    }
    
    public void deselectAll() {
        FeedDto dto = listSelectionModel.getSelectedObject();
        if (dto == null) {
            return;
        }
        listSelectionModel.setSelected(dto, false);
    }

    public void addFeedOnTop(FeedDto FeedDto) {
        dataProvider.getList().add(0, FeedDto);
    }

    public FeedDto getSelectedFeed() {
        return listSelectionModel.getSelectedObject();
    }
}