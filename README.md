```text
The Veil Mod
Description: Proof of Concept Minecraft Mod made by Tristan Anderson
Copyright (c) 2025 Tristan Anderson
Licensed under the MIT License

theveil/
├─ build/ # Generated/compiled output
├─ gradle/ # Gradle wrapper
│ └─ wrapper/
├─ src/
│ └─ main/
│ ├─ java/
│ │ └─ com/
│ │ └─ trist79/
│ │ └─ veil/
│ │ ├─ TheVeilMod.java # Main mod class
│ │ ├─ common/
│ │ │ ├─ blocks/ # Custom block classes
│ │ │ ├─ items/ # Custom items
│ │ │ ├─ entities/ # Entities/mobs
│ │ │ ├─ fluids/ # Custom fluids
│ │ │ ├─ data/ # Data generators
│ │ │ │ ├─ DataGenerators.java
│ │ │ │ ├─ loot/ # Loot table providers
│ │ │ │ │ ├─ VeilLootTableProvider.java
│ │ │ │ │ └─ subproviders/
│ │ │ │ │ └─ SomeSubLootProvider.java
│ │ │ │ ├─ recipes/ # Recipe providers
│ │ │ │ │ └─ VeilRecipeProvider.java
│ │ │ │ ├─ tags/ # Tag providers
│ │ │ │ │ └─ VeilTagProvider.java
│ │ │ │ └─ models/ # Optional: model data generators
│ │ │ └─ util/ # Utilities, helpers
│ │ └─ client/ # Client-only classes (renderers, GUIs)
│ │ ├─ render/
│ │ └─ gui/
│ └─ resources/
│ ├─ assets/
│ │ └─ theveil/
│ │ ├─ blockstates/
│ │ ├─ models/
│ │ │ ├─ block/
│ │ │ └─ item/
│ │ ├─ textures/
│ │ │ ├─ block/
│ │ │ └─ item/
│ │ └─ lang/
│ └─ data/
│ └─ theveil/
│ ├─ loot_tables/
│ ├─ recipes/
│ └─ tags/
```
