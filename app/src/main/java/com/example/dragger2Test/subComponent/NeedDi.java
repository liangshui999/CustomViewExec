package com.example.dragger2Test.subComponent;

import com.example.dragger2Test.subComponent.another.DaggerAComponentZ;

import javax.inject.Inject;

public class NeedDi {

    @Inject
    public A1 a1;

    @Inject
    public B1 b1;

    @Inject
    public B2 b2;

    @Inject
    public NeedDi(){
        /*DaggerBComponent
                .builder()
                .aComponent(DaggerAComponent.builder().build())
                .build()
                .inject(this);*/

        DaggerAComponentZ
                .builder()
                .build()
                .createBSubComponent()
                .inject(this);


    }

}
