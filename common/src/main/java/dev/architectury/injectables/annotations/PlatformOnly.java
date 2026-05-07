package dev.architectury.injectables.annotations;

public @interface PlatformOnly {
    String FABRIC = "fabric";
    String FORGE = "forge";

    String value();
}