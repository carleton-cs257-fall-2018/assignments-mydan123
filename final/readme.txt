Name:
Daniel Busis

Description:
My project is a pseudo-3D game in which the player walks down rectangular, underground hallways. 
The hallways are represented as a grid of squares that the player can be standing in, and a 
minimap on the irght side of the screen displays areas the player has discovered. The purpose
is to find a key somehwere on the map and then use it to unlock a door somewhere else.

Why MVC is appropriate:
MVC is appropriate for my project because my project consists of a single model that has data
about a poition, a group of squares, and some properties such as whether a key has been picked
up and the direction a player is facing. These properties need to be viewed in a variety of ways:
there is a first-person perspective of a tunnel, a minimap that displays discovered squares and
the player's position within them, and an inventory that diplays whether the player has acquired
the key. 

Core classes:
 - Main: Uses a .fxml file to set up the stage.
 - Controller: Handles key events, tells the model and views to update themselves.
 - DungeonModel: Contains information about what is in each square of the dungeon,
       the player's position, the direction the player is facing, which squares have
	   been revealed, and whther the player has the key.
 - DungeonView: Handles displaying a first-person perspective of the player's view
       within the dungeon.
 - MapView: Displays a map of the squares of the dungeon which the player has
       discovered.
 - InventoryView: Displays an outline of the key, and fills in that outline when the
       player collects the key.