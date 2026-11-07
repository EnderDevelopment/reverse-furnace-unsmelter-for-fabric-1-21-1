# Minecraft Mod: Reverse Furnace

Reverse Furnace is a Fabric Minecraft mod for 1.21.1 that adds an unsmelter block for reversing loaded smelting recipes. It uses the active server's recipe data to recover ingredients from smelted items, including custom smelting recipes added by other mods, and provides back and forward arrow controls for cycling through alternate inputs.

## Install

Requirements:

- Minecraft 1.21.1
- Fabric Loader 0.16.10 or newer
- Fabric API
- Java 21

To install the mod:

1. Install Fabric Loader for Minecraft 1.21.1.
2. Install Fabric API for Minecraft 1.21.1.
3. Build the project with `./gradlew build`, or obtain the compiled `unsmelter` JAR.
4. Place the JAR in the Minecraft `mods` folder.
5. Launch Minecraft with the Fabric profile.

To build the mod from source:

```sh
./gradlew build
```

The compiled JAR is created in `build/libs`.

## Use

1. Craft the Unsmelter with eight iron ingots surrounding a furnace.
2. Place the Unsmelter in the world.
3. Open it to access its two-slot inventory.
4. Place a smelted item in the input slot.
5. The output slot displays an ingredient from a matching loaded smelting recipe.
6. Use the left and right arrow buttons to cycle through alternate recipe inputs.
7. Take the recovered item from the output slot. One item is removed from the input slot for each recovered item.

The Unsmelter checks smelting recipes loaded by the active server, so recipes supplied by compatible mods can be recognized without adding them to a hard-coded list. Recipe behavior in multiplayer follows the server's loaded recipe data.

---

More Minecraft mod projects by EnderDevelopment: https://github.com/EnderDevelopment

Project page: https://dash.enderdevelopment.com/community/0e99656c-d400-48f9-ba60-a06247427522?utm_source=github&utm_medium=readme&utm_campaign=auto_publish&utm_content=0e99656c-d400-48f9-ba60-a06247427522
