AP PROJECT - ANGRY BIRDS using LibGDX

INTRO
We make Angry Birds game! This game uses the LibGDX framework to create a fun and interactive experience where player click on the screen where we have defined where to click to increase decrease or more bounce low bounce status to shoot at structures and pigs. Goal is to destroy all pigs in each level. The game features multiple levels, a victory screen, and the ability to replay or progress to the next level.

BONUS:
Where when a bird launch it gets bigger in size and destruct the structures 

SAVING STATUS: 
We have implemented serialization to save the progress using Json
ALL levels except first is locked until and unless the player win it.
As player win, it unlocks next level.

JUNIT
Implemented a test which checks the condition for winning the game

Game Features
When run the code the gaming screen loads and then  a menu page the levels screen is shown in which levels are shown 1,2,3


 After completing a level, a victory screen is shown with options to replay the level or move to the next one.

Game Flow
1. Launcher Screen
When player first launch the game, the loading screen appears for 3 seconds, giving the game time to initialize.
After the loading screen, the game will automatically transition to the Main Menu.

2. Main Menu
The main menu includes three options:
Play Button: Starts the game and takes you to the Levels Menu.
Settings Button
Quit Button: Exits the game.
3. Settings button.
4. Levels Menu
Clicking Play on the main menu takes you to the Levels Menu.

The available levels are displayed:
Level 1 - Start with Level 1.
Level 2 -  Unlockable after completing Level 1.
Level 3 - Unlockable after completing Level 2.

Launching Birds: click on the screen to launch the bird 
Destroy all the pigs by hitting them with the launched birds.

Victory Page - If all pigs are destroyed, a victory screen is shown:
Next Level - Click this next level button to move to the next level.
Replay Button: Click this to restart the current level.
Loose Page: If you fail to destroy all pigs, the loss screen appears:
Retry Button: Click this to restart the level.
Back to Menu Button: Click this to return to the main menu.

As player complete each level, the next level will unlock, allowing you to progress through the game.
If you fail a level, you can retry it as many times as you need until you win.
