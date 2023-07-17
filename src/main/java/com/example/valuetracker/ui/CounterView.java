package com.example.valuetracker.ui;

import com.example.valuetracker.entity.CounterEntity;
import com.example.valuetracker.service.CounterService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.Route;

@Route
public class CounterView extends VerticalLayout {
    private final CounterService counterService;
    private final TextField counterField;

    private final Binder<CounterEntity> binder;

    public CounterView(CounterService counterService) {
        this.counterService = counterService;
        this.counterField = new TextField("Counter Value");
        this.binder = new Binder<>(CounterEntity.class);

        loadCounterValueFromDatabase();
        setupIncrementButton();
        setupValueChangeListener();
    }


    private void loadCounterValueFromDatabase() {
        // Load counter value from the database or create a new entity if not found
        CounterEntity counterEntity = counterService.getCounterEntity();
        binder.setBean(counterEntity);

        // Bind the TextField to the CounterEntity using a converter
        binder.forField(counterField)
                .withConverter(new StringToIntegerConverter("Invalid number format!"))
                .bind(CounterEntity::getCounterValue, CounterEntity::setCounterValue);
    }

    private void setupIncrementButton() {
        // Create and set up the "Increment" button
        Button incrementButton = new Button("Increment", event -> {
            binder.getBean().setCounterValue(Integer.parseInt(counterField.getValue()));
            CounterEntity savedEntity = counterService.incrementAndSaveCounter(binder.getBean());
            binder.setBean(savedEntity);

            Notification.show("Value incremented !!!");
        });

        // Add the "Increment" button to the layout
        add(counterField, incrementButton);
    }

    private void setupValueChangeListener() {
        // Add a value change listener to automatically save changes when the field value is modified
        counterField.addValueChangeListener(event -> {
            binder.validate(); // Trigger validation to update the bean in the binder
            if (!binder.isValid()) {
                Notification.show("Invalid number format!");
            } else {
                counterService.incrementAndSaveCounter(binder.getBean());
                Notification.show("Value updated !!!");
            }
        });
    }
}