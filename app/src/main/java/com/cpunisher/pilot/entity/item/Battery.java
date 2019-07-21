package com.cpunisher.pilot.entity.item;

import com.cpunisher.pilot.R;
import com.cpunisher.pilot.entity.Player;
import com.cpunisher.pilot.game.GameConstSettings;
import com.cpunisher.pilot.game.GameControl;

public class Battery extends Item {

    public Battery(GameControl gameControl) {
        super(gameControl);
        this.bindTexture(R.drawable.battery);
    }

    @Override
    public void collisionWithPlayer(Player player) {
        player.setGodMode(GameConstSettings.BATTERY_GOD_MODE);
    }
}
