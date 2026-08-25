package turniplabs.halplibe.util.deathcause.vanilla;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Mob;
import turniplabs.halplibe.util.deathcause.DeathCause;

public class DeathCauseGeneric extends DeathCause {
    String keyShard = "generic";

    public DeathCauseGeneric(){
        super();
    }

    public DeathCauseGeneric(Mob victim){
        super(victim);
    }

    public DeathCauseGeneric(Mob victim, String keyShard){
        super(victim);
        this.keyShard = keyShard;

    }

    @Override
    protected String getTranslationKeyShard() {
        return this.keyShard;
    }

    @Override
    protected void serializeAdditional(CompoundTag tag) {
        tag.putString("halplibe:key_shard", this.keyShard);
    }

    @Override
    protected void deserializeAdditional(CompoundTag tag) {
        if(tag.containsKey("halplibe:key_shard")){
            this.keyShard = tag.getString("halplibe:key_shard");
        }
    }
}