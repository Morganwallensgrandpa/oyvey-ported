// Listen for entity spawn events on the server
ServerEvents.entitySpawn(event => {
    let entity = event.entity

    // Check if the spawned entity is an arrow
    if (entity.type === 'minecraft:arrow' || entity.type === 'minecraft:spectral_arrow') {
        
        // Get the current motion (velocity) vector
        let motion = entity.getMotion()
        
        // Multiply the speed by 3.0 (increase or decrease this number to change speed)
        let speedMultiplier = 3.0
        
        entity.setMotion(
            motion.x * speedMultiplier,
            motion.y * speedMultiplier,
            motion.z * speedMultiplier
        )
        
        // Tell the server to update the entity's movement tracking
        entity.hasImpulse = true
    }
})
