# The Veil Mod

**Description:** Proof of Concept Minecraft Mod made by Tristan Anderson

**Copyright:** © 2025 Tristan Anderson

**License:** [MIT License](https://opensource.org/licenses/MIT)
```text
├📁theveil/
├── 📁 src/
│ ├── 📁 generated/resources/data/
│ │ ├── 📁 minecraft/ 🔹 Overwritten Minecraft JSONs go here
│ │ └── 📁 theveil/ 🔹 RunData-generated Veil Mod JSONs go here
│ ├── 📁 main/
│ │ ├── 📁 java/com/trist79/veil/
│ │ │ ├── 📁 common/
│ │ │ │ ├── 📁 blocks/ ✅ Java block classes
│ │ │ │ ├── 📁 data/
│ │ │ │ │ ├── 📁 loot/
│ │ │ │ │ │ ├── 📁 subproviders/
│ │ │ │ │ │ │ └── TestDungeonLootSubProvider.java ✅
│ │ │ │ │ │ └── VeilLootTableProvider.java ✅
│ │ │ │ │ ├── 📁 tags/
│ │ │ │ │ │ ├── VeilBlockTagsProvider.java ✅
│ │ │ │ │ │ ├── VeilDataGenerators.java ✅
│ │ │ │ │ │ └── VeilRecipeProvider.java ✅
│ │ │ │ ├── 📁 entities/ ✅ Entity classes
│ │ │ │ ├── 📁 items/ ✅ Item classes
│ │ │ │ └── 📁 registry/
│ │ │ │ └── VeilRegistry.java ✅
│ │ │ │ └── 📁 util/ ✅ Utility classes
│ │ │ │ └── 📁 world/
│ │ │ │ ├── VeilChunkGenerator.java ✅
│ │ │ │ ├── VeilDimension.java ✅
│ │ │ │ ├── VeilDimensionRegistry.java ✅
│ │ │ │ └── VeilTeleporter.java ✅
│ │ │ │ ├── package-info.java ✅
│ │ │ │ ├── Config.java ✅
│ │ │ │ ├── TheVeilMod.java ✅
│ │ │ │ └── TheVeilModClient.java ✅
│ │ ├── 📁 resources/
│ │ │ ├── 📁 assets/ 🔹 Currently unused; will house non-code assets like textures, models, etc
│ │ │ ├── 📁 data/ 🔹 Currently unused; will house manually edited data files
│ │ │ └── 📁 META-INF/
│ │ │ └── neoforge.mods.toml ✅ Mod metadata
├── 📁 Base Repo Files, Gradle Scripts

Version History:
0.0.1 10/17/2025 - Initial Version
0.0.2 10/20/2025 - Successful Veil Dimension Generaration
0.0.3 10/20/2025 - Crystallized Chorus Fruit Teleportation
