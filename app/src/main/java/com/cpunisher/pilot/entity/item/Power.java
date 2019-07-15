package com.cpunisher.pilot.entity.item;

import com.cpunisher.pilot.R;
import com.cpunisher.pilot.entity.Player;
import com.cpunisher.pilot.game.GameControl;

public class Power extends Item {

    public Power(GameControl gameControl) {
        super(gameControl);
        this.bindTexture(R.drawable.bullet_player);
    }

    @Override
    public void collisionWithPlayer(Player player) {
        player.setPower(player.getPower() + 1);
    }
}
