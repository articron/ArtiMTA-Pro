package org.dreambot.articron;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.MessageListener;
import org.dreambot.api.wrappers.widgets.message.Message;
import org.dreambot.articron.behaviour.EnableRunning;
import org.dreambot.articron.data.AlchemyDrop;
import org.dreambot.articron.data.MTARoom;
import org.dreambot.articron.fw.Manager;
import org.dreambot.articron.ui.ProfilePicker;

import java.awt.*;

/**
 * Author: Articron
 * Date:   14/10/2017.
 */
@ScriptManifest(
        category = Category.MINIGAME,
        name = "ArtiMTA PRO",
        author = "Articron",
        version = 1.41D,
        description = "Does the MTA minigame to obtain infinity items. 250-400K/hr, profitable magic xp! " +
        "See the script thread for user instructions!"
)
public class ArtiMTA extends CronScript implements MessageListener {

    @Override
    public void onStart() {
        setContext();
        getWalking().setRunThreshold(Calculations.random(30,50));
        Manager.init(getContext());
        Manager.commit(
                new EnableRunning().when(
                        () -> !getWalking().isRunEnabled() && getWalking().getRunEnergy() >= getWalking().getRunThreshold()
                )
        );
        new ProfilePicker(this);
    }



    @Override
    public int onLoop() {
        return Manager.cycleNodes();
    }

    @Override
    public void onPaint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST, 100);
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_PURE);
        getContext().getPaint().onPaint(g2);
    }

    @Override
    public void onGameMessage(Message message) {
        if (getContext().getMTA().getCurrentRoom() == MTARoom.ALCHEMY) {
            getContext().getMTA().getAlchemyHandler().setFoundItem(
                    AlchemyDrop.forMessage(message.getMessage()), getGameObjects().closest("Cupboard").getTile());
        }
        if (!getContext().getMTA().isOutside()) {
            if (message.getMessage().contains("You do not have enough") && message.getMessage().contains("to cast this spell")) {
                MethodProvider.log("[SCRIPT STOP]");
                MethodProvider.log("Reason: ");
                MethodProvider.log("\"Out of runes to cast " +
                        getContext().getMTA().getRoom(getContext().getMTA().getCurrentRoom()).getSpell().getSpellName() +
                        " in room: " + getContext().getMTA().getCurrentRoom().name()+"\"");
                MethodProvider.log("The bot will attempt to leave the room before shutting down");
                getContext().shutdown();
            }
        }
    }

    @Override
    public void onPlayerMessage(Message message) {

    }

    @Override
    public void onTradeMessage(Message message) {

    }

    @Override
    public void onPrivateInMessage(Message message) {

    }

    @Override
    public void onPrivateOutMessage(Message message) {

    }
}
