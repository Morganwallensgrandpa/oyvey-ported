package me.alpha432.oyvey.features.modules.player;

import me.alpha432.oyvey.event.impl.network.PacketEvent;
import me.alpha432.oyvey.event.system.Subscribe;
import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;

/**
 * Mod Name: FastArrows
 * Description: Instantly multiplies the velocity of all fired arrows upon spawning.
 * Platform: Custom Minecraft Utility Client (Java)
 */
public class FastArrowModule extends Module {

    // Module constructor establishing name, description, and UI category
    public FastArrowModule() {
        super("FastArrows", "Makes your fired arrows shoot significantly faster", Category.PLAYER);
    }

    // Listens for outgoing player action packets to modify projectile data
    @Subscribe
    private void onPacketSend(PacketEvent.Send event) {
        
        // Target the local player character
        if (mc.player == null || mc.world == null) return;

        // Iterate through nearby entities to find arrows spawned by the player
        mc.world.getEntitiesNeighbors().forEach(entity -> {
            if (entity instanceof Arrow) {
                Arrow arrow = (Arrow) entity;

                // Ensure the arrow was shot by the client player
                if (arrow.getOwner() == mc.player) {
                    
                    // Define the speed multiplier (e.g., 3.5 = 350% faster)
                    double speedMultiplier = 3.5;

                    // Apply the velocity multiplication across all physics axes
                    arrow.setDeltaMovement(
                        arrow.getDeltaMovement().x * speedMultiplier,
                        arrow.getDeltaMovement().y * speedMultiplier,
                        arrow.getDeltaMovement().z * speedMultiplier
                    );
                }
            }
        });
    }
}
