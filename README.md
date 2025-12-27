# SmartHUD

A customizable HUD mod for Minecraft Fabric that displays coordinates, direction, dimension information, and more.

## Features

- **Current Coordinates**: Display your current X, Y, Z position
- **Direction**: Shows cardinal direction and angle (N, NE, E, SE, S, SW, W, NW)
- **Dimension Coordinates**: Automatically converts between Nether and Overworld coordinates
- **Looking At Block**: Shows coordinates of the block you're looking at
- **Biome**: Displays current biome
- **Time**: Shows in-game time in 24-hour format
- **Fully Customizable**: Configure position, colors, and what information to display via Mod Menu

## Requirements

- Minecraft 1.21 - 1.21.11
- Fabric Loader 0.18.1+
- Fabric API 0.140.2+1.21.11
- Mod Menu (recommended)
- Cloth Config (included)

## Installation

1. Install Fabric Loader
2. Download the latest version of SmartHUD
3. Place the `.jar` file in your `mods` folder
4. Launch Minecraft

## Configuration

Press **Mod Menu** button in the main menu, find SmartHUD, and click the config button to customize:
- HUD position on screen
- Text color and shadow
- Which elements to display
- Offset from screen edges

## Languages Supported

- English (en_us)
- Korean (ko_kr)

## Building

```bash
./gradlew build
```

The built mod will be in `build/libs/`.

## License

MIT License
