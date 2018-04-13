package com.example.dragger2Test.subComponent.another;

import com.example.dragger2Test.subComponent.BModule;
import com.example.dragger2Test.subComponent.NeedDi;

import dagger.Subcomponent;

@Subcomponent(modules = {BModule.class})
public interface BSubComponent {

    void inject(NeedDi needDi);
}
