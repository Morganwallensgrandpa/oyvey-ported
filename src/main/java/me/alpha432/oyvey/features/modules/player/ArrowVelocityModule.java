package me.alpha432.oyvey.features.modules.player;

import me.alpha432.oyvey.event.impl.network.PacketEvent;
import me.alpha432.oyvey.event.system.Subscribe;
import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;

/**
 * Mod Name: FastArrows
 * Description: Instantly multiplies the velocity of all user-fired arrows upon spawning.
 * Platform: Custom Minecraft Utility Client (Java)
 */
public class FastArrowsModule extends Module {

    // Module constructor establishing name, description, and UI category
    public FastArrowsModule() {
        super("FastArrows", "Multiplies the initial velocity of all arrows you shoot", Category.PLAYER);
    }

    // Listens for outgoing player action packets to modify projectile data safely
    @Subscribe
    private void onPacketSend(PacketEvent.Send event) {
        // Null checks to prevent crashes if the player isn't fully loaded into a world
        if (mc.player == null || mc.world == null) return;

        // Iterate through all nearby entities in the current world chunk loading range
        mc.world.getEntitiesNeighbors().forEach(entity -> {
            
            // Target all classes that inherit from AbstractArrow (Arrows, Spectral Arrows, Tridents)
            if (entity instanceof AbstractArrow) {
                AbstractArrow arrow = (AbstractArrow) entity;

                // Owner check ensures you only accelerate arrows shot by your own player character
                if (arrow.getOwner() == mc.player) {
                    
                    // Define the speed multiplier (e.g., 4.0 = 400% faster)
                    // Note: Settings above 6.0 may cause arrows to pass through block corners
                    double velocityMultiplier = 4.0;

                    // Apply the multiplication uniformly across all physical 3D axes
                    arrow.setDeltaMovement(
                        arrow.getDeltaMovement().x * velocityMultiplier,
                        arrow.getDeltaMovement().y * velocityMultiplier,
                        arrow.getDeltaMovement().z * velocityMultiplier
                    );
                    
                    // Force the entity to update its state immediately
                    arrow.hasImpulse = true;
                }
            }
        });
    }
}
