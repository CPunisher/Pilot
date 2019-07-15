package com.cpunisher.pilot.entity.item;

import com.cpunisher.pilot.R;
import com.cpunisher.pilot.entity.Player;
import com.cpunisher.pilot.game.GameControl;

public class Heart extends Item {

    public Heart(GameControl gameControl) {
        super(gameControl);
        this.bindTexture(R.drawable.heart);
    }

    @Override
    public void collisionWithPlayer(Player player) {
        player.incHeart(1);
    }
}
