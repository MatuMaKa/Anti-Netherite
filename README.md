# Anti-Netherite
Disable Netherite In Your Server

## Config File
```access transformers
enabled: true # Set to false to disable the feature (REQUIRES A RESTART)

# PLAYERS WITH PERMISSION
# antinetherite.bypass CAN BYPASS THE RESTRICTIONS

antinetherite:
  block-placing: true # Disable placing netherite blocks and ancient debris
  block-breaking: true # Disable breaking netherite blocks and ancient debris
  crafting: true # Disable crafting netherite items
  smelting: true # Disable smelting netherite scraps into netherite ingots
  smithing: true # Disable smithing netherite items
  enchanting: true # Disable enchanting netherite items

messages:
  reload-message: "&aAnti-Netherite configuration has been reloaded. &cIF YOU CHANGED 'enabled' THEN RESTART THE SERVER"
  usage-message: "&cUsage: %command%" # %command% is a placeholder
  disabled-message: "&cNetherite is disabled! You can't %action%." # %action% is a placeholder
```