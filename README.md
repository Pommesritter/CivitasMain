# The Civitas server project

CivitasServer is supposed to be an *RPG gameserver interface* which creates and manages the game world, player lists, authentication, leveling curves, trading, game dynamics etc.
A client will be written later. Maybe i will write a Spigot plugin to test out the main functionalities provided by a CivitasServer. In that case, the Spigot server would be 
a client of the Civitas Server. Awesome!


### If you do have some questions or want to know more, feel free to take a look at the [wiki](https://gitlab.com/herbertsfundgrube/civitasserver/wikis/Home)

CivitasMain is the core element of this interface, providing a filesystem (german "Dateisystem"), aswell as it comes with all the saveable classes which can be used by 
Civitas Server plugins.