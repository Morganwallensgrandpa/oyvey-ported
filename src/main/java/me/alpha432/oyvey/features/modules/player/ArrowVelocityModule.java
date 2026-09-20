package me.alpha432.oyvey.features.modules.player;

import me.alpha432.oyvey.event.impl.network.PacketEvent;
import me.alpha432.oyvey.event.system.Subscribe;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.setting.Setting;
import net.minecraft.world.entity.projectile.AbstractArrow;

/**
 * Mod Name: FastArrows
 * Description: Multiplies arrow velocity with an adjustable GUI speed slider.
 * Template Base: OyVey Github Base Client
 */
public class FastArrowsModule extends Module {

    // Creates an adjustable decimal slider setting inside the OyVey GUI interface
    // Parameters: SettingName, DefaultValue, MinValue, MaxValue
    public final Setting<Double> speedMultiplier = this.register(new Setting<>("Speed", 3.5, 1.0, 10.0));

    public FastArrowsModule() {
        super("FastArrows", "Multiplies the initial velocity of all arrows you shoot", Category.PLAYER);
    }

    @Subscribe
    private void onPacketSend(PacketEvent.Send event) {
        if (mc.player == null || mc.world == null) return;

        mc.world.getEntitiesNeighbors().forEach(entity -> {
            if (entity instanceof AbstractArrow) {
                AbstractArrow arrow = (AbstractArrow) entity;

                // Restrict changes strictly to arrows spawned by the client player
                if (arrow.getOwner() == mc.player) {
                    
                    // Pull the multiplier value directly chosen on the GUI slider config
                    double multiplier = this.speedMultiplier.getValue();

                    arrow.setDeltaMovement(
                        arrow.getDeltaMovement().x * multiplier,
                        arrow.getDeltaMovement().y * multiplier,
                        arrow.getDeltaMovement().z * multiplier
                    );
                    
                    arrow.hasImpulse = true;
                }
            }
        });
    }
}
