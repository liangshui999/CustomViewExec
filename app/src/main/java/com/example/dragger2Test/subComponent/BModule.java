package com.example.dragger2Test.subComponent;

import dagger.Module;
import dagger.Provides;

@Module
public class BModule {

    @Provides
    public B1 provideB1(A1 a1){
        return new B1(a1);
    }

    /**
     * 该module所对应的component依赖AComponent，而AComponent中并没有暴露出A2，
     * 因此这里的编译会无法通过
     */
    @Provides
    public B2 provideB2(A2 a2){
        return new B2(a2);
    }

}
