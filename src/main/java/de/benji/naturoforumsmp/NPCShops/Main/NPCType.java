package de.benji.naturoforumsmp.NPCShops.Main;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import org.bukkit.Material;

public enum NPCType {
    Villager(EntityType.VILLAGER, Material.VILLAGER_SPAWN_EGG, "§6Villager"),
    Enderman(EntityType.ENDERMAN, Material.ENDERMAN_SPAWN_EGG, "§6Enderman"),
    Silverfish(EntityType.SILVERFISH, Material.SILVERFISH_SPAWN_EGG, "§6Silverfish"),
    Skeleton(EntityType.SKELETON, Material.SKELETON_SPAWN_EGG, "§6Skeleton"),
    SkeletonHorse(EntityType.SKELETON_HORSE, Material.SKELETON_HORSE_SPAWN_EGG, "§6 Skeleton Horse"),
    Slime(EntityType.SLIME, Material.SLIME_SPAWN_EGG, "§6Slime"),
    Snowman(EntityType.SNOW_GOLEM, Material.SNOW_GOLEM_SPAWN_EGG, "§6Snowman"),
    Mule(EntityType.MULE, Material.MULE_SPAWN_EGG, "§6Mule"),
    MushroomCow(EntityType.MOOSHROOM, Material.MOOSHROOM_SPAWN_EGG, "§6Mushroom Cow"),
    Ocelot(EntityType.OCELOT, Material.OCELOT_SPAWN_EGG, "§6Ocelot"),
    Spider(EntityType.SPIDER, Material.SPIDER_SPAWN_EGG, "§6Spider"),
    Squid(EntityType.SQUID, Material.SQUID_SPAWN_EGG, "§6Squid"),
    Panda(EntityType.PANDA, Material.PANDA_SPAWN_EGG, "§6Panda"),
    Stray(EntityType.STRAY, Material.STRAY_SPAWN_EGG, "§6Stray"),
    Parrot(EntityType.PARROT, Material.PARROT_SPAWN_EGG, "§6Parrot"),
    Strider(EntityType.STRIDER, Material.STRIDER_SPAWN_EGG, "§6Strider"),
    Phantom(EntityType.PHANTOM, Material.PHANTOM_SPAWN_EGG, "§6Phantom"),
    Pig(EntityType.PIG, Material.PIG_SPAWN_EGG, "§6Pig"),
    Piglin(EntityType.PIGLIN, Material.PIGLIN_SPAWN_EGG, "§6 Piglin"),
    Piglinbrute(EntityType.PIGLIN_BRUTE, Material.PIGLIN_BRUTE_SPAWN_EGG, "§6  Piglinbrute"),
    Pillager(EntityType.PILLAGER, Material.PILLAGER_SPAWN_EGG, "§6Pillager"),
    Polarbear(EntityType.POLAR_BEAR, Material.POLAR_BEAR_SPAWN_EGG, "§6Polarbear"),
    Trader(EntityType.WANDERING_TRADER, Material.WANDERING_TRADER_SPAWN_EGG, "§6Trader"),
    Tropicalfish(EntityType.TROPICAL_FISH, Material.TROPICAL_FISH_SPAWN_EGG, "§6Tropicalfish"),
    Pufferfish(EntityType.PUFFERFISH, Material.PUFFERFISH_SPAWN_EGG, "§6Pufferfish"),
    Turtle(EntityType.TURTLE, Material.TURTLE_SPAWN_EGG, "§6Turtle"),
    Rabbit(EntityType.RABBIT, Material.RABBIT_SPAWN_EGG, "§6Rabbit"),
    Vex(EntityType.VEX, Material.VEX_SPAWN_EGG, "§6Vex"),
    Ravager(EntityType.RAVAGER, Material.RAVAGER_SPAWN_EGG, "§6Ravager"),
    Salmon(EntityType.SALMON, Material.SALMON_SPAWN_EGG, "§6Salmon"),
    Vindicator(EntityType.VINDICATOR, Material.VINDICATOR_SPAWN_EGG, "§6Vindicator"),
    Sheep(EntityType.SHEEP, Material.SHEEP_SPAWN_EGG, "§6Sheep"),
    Witch(EntityType.WITCH, Material.WITCH_SPAWN_EGG, "§6Witch"),
    Witherskeleton(EntityType.WITHER_SKELETON, Material.WITHER_SKELETON_SPAWN_EGG, "§6 Witherskeleton"),
    Wolf(EntityType.WOLF, Material.WOLF_SPAWN_EGG, "§6Wolf"),
    Zoglin(EntityType.ZOGLIN, Material.ZOGLIN_SPAWN_EGG, "§6Zoglin"),
    Zombie(EntityType.ZOMBIE, Material.ZOMBIE_SPAWN_EGG, "§6Zombie"),
    ZombieVillager(EntityType.ZOMBIE_VILLAGER, Material.ZOMBIE_VILLAGER_SPAWN_EGG, "§6 Zombie Villager"),
    ZombifiedPiglin(EntityType.ZOMBIFIED_PIGLIN, Material.ZOMBIFIED_PIGLIN_SPAWN_EGG, "§6ZombifiedPiglin"),
    Fox(EntityType.FOX, Material.FOX_SPAWN_EGG, "§6Fox"),
    Bat(EntityType.BAT, Material.BAT_SPAWN_EGG, "§6Bat"),
    Bee(EntityType.BEE, Material.BEE_SPAWN_EGG, "§6Bee"),
    Blaze(EntityType.BLAZE, Material.BLAZE_SPAWN_EGG, "§6Blaze"),
    Cat(EntityType.CAT, Material.CAT_SPAWN_EGG, "§6Cat"),
    CaveSpider(EntityType.CAVE_SPIDER, Material.CAVE_SPIDER_SPAWN_EGG, "§6CaveSpider"),
    Guardian(EntityType.GUARDIAN, Material.GUARDIAN_SPAWN_EGG, "§6Guardian"),
    Chicken(EntityType.CHICKEN, Material.CHICKEN_SPAWN_EGG, "§6Chicken"),
    Hoglin(EntityType.HOGLIN, Material.HOGLIN_SPAWN_EGG, "§6Hoglin"),
    Cod(EntityType.COD, Material.COD_SPAWN_EGG, "§6Cod"),
    Horse(EntityType.HORSE, Material.HORSE_SPAWN_EGG, "§6Horse"),
    Cow(EntityType.COW, Material.COW_SPAWN_EGG, "§6Cow"),
    Husk(EntityType.HUSK, Material.HUSK_SPAWN_EGG, "§6Husk"),
    Creeper(EntityType.CREEPER, Material.CREEPER_SPAWN_EGG, "§6Creeper"),
    Illusioner(EntityType.ILLUSIONER, Material.DROWNED_SPAWN_EGG, "§6Illusioner"),
    Dolphin(EntityType.DOLPHIN, Material.DOLPHIN_SPAWN_EGG, "§6Dolphin"),
    IronGolem(EntityType.IRON_GOLEM, Material.IRON_GOLEM_SPAWN_EGG, "§6Iron Golem"),
    Donkey(EntityType.DONKEY, Material.DONKEY_SPAWN_EGG, "§6Donkey"),
    Drowned(EntityType.DROWNED, Material.DROWNED_SPAWN_EGG, "§6Drowned"),
    ElderGuardian(EntityType.ELDER_GUARDIAN, Material.ELDER_GUARDIAN_SPAWN_EGG, "§6Elder Guardian"),
    Llama(EntityType.LLAMA, Material.LLAMA_SPAWN_EGG, "§6Llama"),
    Endermite(EntityType.ENDERMITE, Material.ENDERMITE_SPAWN_EGG, "§6Endermite"),
    Magmacube(EntityType.MAGMA_CUBE, Material.MAGMA_CUBE_SPAWN_EGG, "§6Magmacube"),
    Evoker(EntityType.EVOKER, Material.EVOKER_SPAWN_EGG, "§6Evoker"),
    Axolotl(EntityType.AXOLOTL, Material.AXOLOTL_SPAWN_EGG, "§6Axolotl"),
    GlowSquid(EntityType.GLOW_SQUID, Material.GLOW_SQUID_SPAWN_EGG, "§6Glow Squid"),
    Goat(EntityType.GOAT, Material.GOAT_SPAWN_EGG, "§6Goat"),
    Warden(EntityType.WARDEN, Material.WARDEN_SPAWN_EGG, "§6Warden"),
    Frog(EntityType.FROG, Material.FROG_SPAWN_EGG, "§6Frog"),
    Allay(EntityType.ALLAY, Material.ALLAY_SPAWN_EGG, "§6Allay"),
    Camel(EntityType.CAMEL, Material.CAMEL_SPAWN_EGG, "§6Camel"),
    Sniffer(EntityType.SNIFFER, Material.SNIFFER_SPAWN_EGG, "§6Sniffer"),
    Wither(EntityType.WITHER, Material.WITHER_SPAWN_EGG, "§6Wither")
    ;

    public final EntityType<? extends Mob> type;
    public final Material spawnEgg;
    public final String display;

    NPCType(EntityType<? extends Mob> type, Material spawnEgg, String display) {
        this.type = type;
        this.spawnEgg = spawnEgg;
        this.display = display;
    }
}
