package com.tind.game.managers;

import java.util.ArrayList;
import java.util.TreeMap;
import com.tind.game.box2d.Box2DWorld;
import com.tind.game.entity.Entity;
import com.tind.game.map.Chunk;

public class ObjectManager {
    public ArrayList<Entity> entities = new ArrayList<Entity>();
    public TreeMap<Integer, Chunk> chunks = new TreeMap<Integer, Chunk>();
    transient public Chunk currentChunk;
    
    public void clearAll(Box2DWorld box2D) {
        // Clear Entity Box2D parts
        for(Entity e : entities){            
            if (e.body != null) box2D.world.destroyBody(e.body);
            if (e.sensor != null) box2D.world.destroyBody(e.sensor);
        }
        
        entities.clear();
        chunks.clear();
        currentChunk = null;
        box2D.clearAllBodies();
    }
}
