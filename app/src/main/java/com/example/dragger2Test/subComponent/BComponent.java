package com.example.dragger2Test.subComponent;

import dagger.Component;

@Component(modules = {BModule.class}, dependencies = {AComponent.class})
public interface BComponent {
    void inject(NeedDi di);
}
