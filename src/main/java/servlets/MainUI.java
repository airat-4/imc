package servlets;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import entity.Bill;
import service.Service;


@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {
    private Window priceWindow;
    private Window placeWindow;
    private Window cotegoryWindow;
    private Table priceTable;
    private Table placeTable;
    private Table cotegoryTable;
    private CheckBox priseCheckBox;
    private CheckBox placeCheckBox;
    private CheckBox cotegoryCheckBox;
    @Override
    protected void init(VaadinRequest request) {
        initWidgetSettings();
        initPriceWindow();
        initPlaceWindow();
        initCotegoryWindow();
    }

    private void initCotegoryWindow() {
        Window window = new Window("Категории");
        cotegoryWindow = window;
        int x = 0, y = 340;
        boolean visible = true;
        window.setPosition(x, y);
        cotegoryCheckBox.setValue(visible);
        if (visible) {
            window.close();
            addWindow(window);
        }
        final Table table = new Table();
        table.setPageLength(5);
        cotegoryTable = table;
        table.addContainerProperty("Категория товара", String.class, null);
        final Service service = Service.getInstance();
        int i = 0;
        for (String place : service.getCotegories("все")) {
            table.addItem(new Object[]{place}, ++i);
        }
        table.setSelectable(true);
        table.setNullSelectionItemId(0);
        table.setValue(0);
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(table);
        Button button = new Button("Преминить");
        layout.addComponent(button);
        window.setContent(layout);
        button.addClickListener(contextClickEvent -> {
            Object itemId = table.getValue();
            Item item = table.getItem(itemId);
            Property itemProperty = item.getItemProperty("Категория товара");
            int j = 0;
            Object Id = placeTable.getValue();
            Item item1 = placeTable.getItem(Id);
            String str;
            if (item1 != null) {
                Property placeItem = item1.getItemProperty("Место приобретения");
                str = (String) placeItem.getValue();
            } else {
                str = "все";
            }


            placeTable.removeAllItems();
            for (String place : service.getPlaces((String) itemProperty.getValue())) {
                placeTable.addItem(new Object[]{place}, ++j);
            }
            priceTable.removeAllItems();
            for (Bill bill : service.getBills((String) itemProperty.getValue(), str)) {
                priceTable.addItem(new Object[]{bill.getProductName(), bill.getPrice(),
                        bill.getCotegory(), bill.getPlace(), bill.getDate()}, bill.hashCode());
            }

        });
        window.addCloseListener(closeEvent -> cotegoryCheckBox.setValue(false));
    }

    private void initPlaceWindow() {
        Window window = new Window("Места");
        placeWindow = window;
        int x = 0, y = 30;
        boolean visible = true;
        window.setPosition(x, y);
        placeCheckBox.setValue(visible);
        if (visible) {
            window.close();
            addWindow(window);
        }
        final Table table = new Table();
        table.setWidth("230px");
        placeTable = table;
        table.setPageLength(5);
        table.addContainerProperty("Место приобретения", String.class, null);
        final Service service = Service.getInstance();
        int i = 0;
        for (String place : service.getPlaces("все")) {
            table.addItem(new Object[]{place}, ++i);
        }
        table.setSelectable(true);
        table.setNullSelectionItemId(0);
        table.setValue(0);
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(table);
        Button button = new Button("Преминить");
        layout.addComponent(button);
        window.setContent(layout);
        window.addCloseListener(closeEvent -> placeCheckBox.setValue(false));
        button.addClickListener(contextClickEvent -> {
            Object itemId = table.getValue();
            Item item = table.getItem(itemId);
            Property itemProperty = item.getItemProperty("Место приобретения");
            int j = 0;
            Object Id = cotegoryTable.getValue();
            String str;
            Item item1 = cotegoryTable.getItem(Id);
            if (item1 != null) {
                Property placeItem =  item1.getItemProperty("Категория товара");
                str = (String) placeItem.getValue();
            } else {
                str = "все";
            }
            cotegoryTable.removeAllItems();
            for (String place : service.getCotegories((String) itemProperty.getValue())) {
                cotegoryTable.addItem(new Object[]{place}, ++j);

            }
            priceTable.removeAllItems();
            for (Bill bill : service.getBills(str,(String) itemProperty.getValue())) {
                priceTable.addItem(new Object[]{bill.getProductName(), bill.getPrice(),
                        bill.getCotegory(), bill.getPlace(), bill.getDate()}, bill.hashCode());
            }

        });
    }

    private void initWidgetSettings() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponent(new Label("Виджеты: "));
        priseCheckBox = new CheckBox("Приобретенные товары");
        placeCheckBox = new CheckBox("Места");
        cotegoryCheckBox = new CheckBox("Категории");
        horizontalLayout.addComponent(priseCheckBox);
        horizontalLayout.addComponent(placeCheckBox);
        horizontalLayout.addComponent(cotegoryCheckBox);
        setContent(horizontalLayout);
        priseCheckBox.addValueChangeListener(valueChangeEvent -> {
            if(priseCheckBox.getValue()){
                addWindow(priceWindow);
            }else{
                priceWindow.close();
            }
        });

        placeCheckBox.addValueChangeListener(valueChangeEvent -> {
            if(placeCheckBox.getValue()){
                addWindow(placeWindow);
            }else{
                placeWindow.close();
            }
        });

        cotegoryCheckBox.addValueChangeListener(valueChangeEvent -> {
            if(cotegoryCheckBox.getValue()){
                addWindow(cotegoryWindow);
            }else{
                cotegoryWindow.close();
            }
        });
    }

    private void initPriceWindow() {
        Window window = new Window("Приобретенные товары");

        priceWindow = window;
        int x = 235, y = 30;
        boolean visible = true;
        window.setPosition(x, y);
        priseCheckBox.setValue(visible);
        if (visible) {
            window.close();
            addWindow(window);
        }
        final Table table = new Table();
        table.setPageLength(12);
        priceTable = table;
        table.addContainerProperty("Тавар", String.class, null);
        table.addContainerProperty("Цена", Double.class, null);
        table.addContainerProperty("Категория товара", String.class, null);
        table.addContainerProperty("Место приобретения", String.class, null);
        table.addContainerProperty("Дата приобретения", String.class, null);
        final Service service = Service.getInstance();
        for (Bill bill : service.getBills("все", "все")) {
            table.addItem(new Object[]{bill.getProductName(), bill.getPrice(),
                    bill.getCotegory(), bill.getPlace(), bill.getDate()}, bill.hashCode());
        }
        table.setSelectable(true);
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(table);
        window.setContent(layout);
        window.addCloseListener(closeEvent -> priseCheckBox.setValue(false));
    }
}