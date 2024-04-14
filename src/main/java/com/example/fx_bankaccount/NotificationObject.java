package com.example.fx_bankaccount;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.image.Image;
public class NotificationObject {

    FontAwesomeIconView icon;
    String information;
    Image pic;

    public NotificationObject(FontAwesomeIconView icon, String information, Image pic) {
        this.icon = icon;
        this.information = information;
        this.pic = pic;
    }


}
