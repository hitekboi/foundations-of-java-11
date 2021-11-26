package com.tind.game.entity;

import java.util.ArrayList;

import com.tind.game.box2d.Box2DHelper;
import com.tind.game.box2d.Box2DWorld;
import com.tind.game.managers.ObjectManager;
import com.tind.game.Enums;
import com.tind.game.Enums.EntityType;
import com.tind.game.Media;
import com.tind.game.Rumble;
import com.tind.game.map.Chunk;
import com.tind.game.map.Tile;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Bird extends Entity{
    // pos.z is the height off the floor
    private float maxHeight;
    public Tile destTile;
    transient private TextureRegion tRegion;
    
    public Bird(Vector3 pos, Box2DWorld box2d, Enums.EnityState state){
        super();
        maxHeight = setHeight();
        speed = MathUtils.random(20) + 5;
        width = 8;
        height = 8;
        shadow = Media.birdShadow;
        this.pos.set(pos);
        
        this.state = state;
        setup(box2d);
    }

    public Bird(JsonObject e, Box2DWorld box2d, ObjectManager objectManager) {
        super();
        maxHeight = e.get("maxHeight").getAsInt();
        speed = e.get("speed").getAsFloat();
        width = e.get("width").getAsInt();
        height = e.get("height").getAsInt();
        time = e.get("time").getAsFloat();
        texture = Media.tree;
        shadow = Media.birdShadow;
        state = Enums.EnityState.valueOf(e.get("state").getAsString());
        
        Gson gson = new Gson();
        this.pos.set(gson.fromJson(e.get("pos"), Vector3.class));
        
        if(e.get("destTile") != null){
            JsonObject JSONtile = e.get("destTile").getAsJsonObject();
            destTile = getTile(JSONtile, objectManager);
            getVector(destTile.pos);
        }
        
        if(e.get("currentTile") != null){ 
            JsonObject JSONtile = e.get("currentTile").getAsJsonObject();          
            currentTile = getTile(JSONtile, objectManager);
        }
                
        setup(box2d);
    } 
    
    private Tile getTile(JsonObject json, ObjectManager objectManager){
        int chunkNumber = json.get("chunk").getAsInt();
        int tileRow = json.get("row").getAsInt();
        int tileCol = json.get("col").getAsInt();
        
        return objectManager.chunks.get(chunkNumber).tiles.get(tileRow).get(tileCol);
    }
    
    private void setup(Box2DWorld box2d){
        type = EntityType.BIRD;
        texture = Media.tree; // Will be replaced 
        body = Box2DHelper.createBody(box2d.world, width/2, height/2, width/4, 0, pos, BodyDef.BodyType.StaticBody);
        sensor = Box2DHelper.createSensor(box2d.world, width, height*.85f, width/2, height/3, pos, BodyDef.BodyType.DynamicBody);     
        hashcode = sensor.getFixtureList().get(0).hashCode();
        ticks = true;
    }

    @Override
    public void draw(SpriteBatch batch){
        //System.out.println(pos);
        setTextureRegion();
        setFlipped();
        
        batch.draw(Media.birdShadow, pos.x, pos.y);
        if(tRegion != null){
            batch.draw(tRegion, pos.x, pos.y + pos.z);
        }
    }
    
    @Override
    public void tick(float delta, Chunk chunk){  
        if(isHovering()){
            setLanding();
        } else if(isLanding()){
            land(); 
        } else if(needsDestination()){
            newDestinationOrHover(delta, chunk);
        } else if(hasDestination()) {
            moveToDestination(delta);
            clearDestination();
        } else if(isNotAirBorn()){
            setNewState(delta);
        }

        if(isFlying()){
            checkFlyHeight();
            toggleHitboxes(false);
        }
    }

    @Override
    public void interact(Entity entity){
        if(entity.inventory != null){
            entity.inventory.addEntity(this);
            remove = true;
            Rumble.rumble(1, .2f);
        }
    }
    
    private void toggleHitboxes(boolean b) {
        body.setActive(b);
        sensor.setActive(b);
    }

    private void setNewState(float delta) {
        if(coolDown > 0){
            coolDown -= delta;
            if(isWalking()){
                walk(delta);
            }
        } else {
            if(MathUtils.randomBoolean(.2f)){
                state = Enums.EnityState.FLYING;
            } else if(MathUtils.randomBoolean(.5f)) {
                state = Enums.EnityState.FEEDING;
                coolDown = .5f;
            } else if(MathUtils.randomBoolean(.3f)) {
                state = Enums.EnityState.WALKING;
                coolDown = 1f;
            }      
        }     
    }

	private void clearDestination() {
        if(isAtDestination()){
            destVec = null;
            destTile = null;
        } 
    }

    private void updatePositions() {
        sensor.setTransform(body.getPosition(),0);
        pos.x = body.getPosition().x - width/2;
        pos.y = body.getPosition().y - height/4;    
    }
    
    private void setTextureRegion(){
        if(isFlying() || isLanding()){
            tRegion = Media.birdFlyAnim.getKeyFrame(time, true);
        } else if(isWalking()){
            tRegion = Media.birdWalkAnim.getKeyFrame(time, true);
        } else if(isFeeding()){
            tRegion = Media.birdPeckAnim.getKeyFrame(time, true);
        } else if(isWalking()){
            tRegion = Media.birdWalkAnim.getKeyFrame(time, true);
        }
    }
    
    private void setFlipped(){
        if(destVec != null){
            if(destVec.x > 0 && !tRegion.isFlipX()){
                tRegion.flip(true, false); 
            } else if(destVec.x < 0 && tRegion.isFlipX()) {
                tRegion.flip(true, false);
            }   
        }
    }
    
    private void moveToDestination(float delta) { 
        // https://github.com/libgdx/libgdx/wiki/Interpolation
        // BUG: Interpolation after loading BIRD.
        //body.setTransform(body.getPosition().interpolate(new Vector2(destTile.pos.x + width, destTile.pos.y + height), delta * speed / 4, Interpolation.smooth), 0);      
        body.setTransform(body.getPosition().x + (destVec.x * speed * delta), body.getPosition().y + (destVec.y * speed * delta), 0);
        
        updatePositions();
    }
    
    private float setHeight() {
        return MathUtils.random(10) + 10;
    }
    
    private void checkFlyHeight() {
        if (isNotHigh()) pos.z += 0.1; 
        if (isTooHigh()) pos.z -= 0.1;     
    }

    private void land() {
        if (isAirBorn()) pos.z -= 0.5; 
        if(pos.z <= 0){ 
            // Landed
            pos.z = 0;
            state = Enums.EnityState.NONE;
            toggleHitboxes(true);
        }
    }
    
    private void setLanding() {
        if(MathUtils.randomBoolean(.05f)){
            state = Enums.EnityState.LANDING;  
        }
    }
    
    private void newDestinationOrHover(float delta, Chunk chunk) {
        // 15% chance a new destination is set, unless over water then always
        // get a new destination
        if(MathUtils.randomBoolean(.85f) || (currentTile != null && currentTile.isWater())){
            setDestination(delta, chunk);
            maxHeight = setHeight(); 
        } else {
            state = Enums.EnityState.HOVERING;
        }
    }

    private void setDestination(float delta, Chunk chunk){
        for(ArrayList<Tile> row : chunk.tiles){
            if(destTile != null) break;
            
            for(Tile tile : row){
                if (tile.isGrass() && MathUtils.random(100) > 99 && tile != currentTile){
                    destTile = tile;
                    getVector(destTile.pos);
                    break;
                }
            }
        }
    }
   
    private void walk(float delta) {
    	if(currentTile != null && currentTile.isPassable()){
    		if(tRegion.isFlipX()){
                body.setTransform(body.getPosition().x - speed / 4 * delta, body.getPosition().y,0);
            } else {
                body.setTransform(body.getPosition().x + speed / 4 * delta, body.getPosition().y,0);
            }
            updatePositions();		
    	}
	}
    
    private boolean hasDestination() {
        return destVec != null;
    }
    
    private boolean isAtDestination() {
        // TODO This is a temp fix as dest and current tiles are not loaded from JSON
        if (currentTile == null || destTile == null) return false;
        return currentTile.pos.epsilonEquals(destTile.pos, 20);
    }
    
    private boolean needsDestination() {
        return destVec == null && isFlying();
    }
    
    public boolean isAirBorn(){
        return pos.z > 0;
    }
    
    public boolean isNotAirBorn(){
        return pos.z == 0;
    }
    
    public boolean isHigh(){
        return pos.z == maxHeight;
    }
    
    public boolean isNotHigh(){
        return pos.z < maxHeight;
    }
    
    public boolean isTooHigh(){
        return pos.z > maxHeight;
    }
    
    private boolean isFlying() {
        return state == Enums.EnityState.FLYING;
    }
    
    private boolean isHovering(){
        return state == Enums.EnityState.HOVERING;
    }
    
    private boolean isLanding(){
        return state == Enums.EnityState.LANDING;
    }

    private boolean isFeeding(){
        return state == Enums.EnityState.FEEDING;
    }
    
    private boolean isWalking(){
        return state == Enums.EnityState.WALKING;
    }
    
}