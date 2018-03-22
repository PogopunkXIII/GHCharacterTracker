# GHCharacterTracker
used to track gloomhaven character inventory and progress

I created this app to be more of a learning android development. Eventually I would like it to be able to replace the physical 
character sheets for the Gloomhaven board game.

At the time of writing this readme the app has 3 activities:
- Main Activity/Character list
- Character sheet
- Scenario tracker

The Main activity just acts as a list of all the players created characters, pressing the "create a new character" button opens the
Character Sheet activity. Here the user can name their character, select a class (only the initally unlocked classes currently supported)
and enter character info. Entering a character level will automatically pull the maximum health and the experience needed to level up
for the class selected. The User can input their current amount of money, and their current amount of XP. Additionally, the user can edit
their max health and required experience to level up, but these will be overridden if the character class or character level is changed.

Pressing the "New Scenario" button on the character sheet opens a Scenario Tracker activity, which tracks the characters health, 
experience gained, and money tokens looted throughout the scenario. The characters health will populate automatically with the max health
from the character sheet, while the exp and money start at 0. These values can be changed throughout the scenario to track character
progress. When a scenario is completed, filling out the Scenario Level and selecting whether the party was successful then pressing "Done"
will automatically add the total looted money (money tokens looted * multiplier for the scenario level + bonus gold for successful completion)
and the total gain experience (experience gained from cards + bonus experience from successful completion based on scenario level) back 
into the character sheet.

## Planned Features:

### In Progress:
- ~~Scenario data retained until scenario is marked completed (Done button pressed)~~
- ~~Adding Characters to list in the Main Activity~~
- ~~Refactor Scenario Data retaining to use a parcelable ScenarioModel object instead of passing individual fields back and forth~~
- ~~Persistent memory for characters (and by extension, unfinished scenarios for those characters) through app close/reopen~~
- **CODE REVIEW WITH THE BOIS**

### Up Next:
- Refactor into MVVM/MVC/VIPER
- Learn proper UI/UX design and rebuild/retool UI (dark theme?)
- Expand Character sheet to track perks
- Expand Character sheet to track Bonus Perk checks
- Expand Character sheet to track Items
- Expand classes to support unlockable classes

## Known Bugs:
- Pressing the "soft" back button in the top left of the Scenario tracker erases all character data from the character sheet
- Pressing the "soft" back button in the top left of the Character Sheet erases all characters saved in the main list (this should be fixed with persistant memory implementation?)
- The first time you start a scenario activity with a brand new character the MAX_HEALTH isn't properly passed into the scenario. This occurs because the scenario is created prior to the character having a meaningful level so we don't know what the max health should be. it defaults to 0. After the first scenario, as long as the character has a meaningful level, the max health will be auto set.
