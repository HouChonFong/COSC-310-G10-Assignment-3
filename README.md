# COSC-310-G10-Assignment-3

This bot consists of dictionaries of keywords and responses, and simple logic to authenticate real life human responses to a scenario. In this case, our bot is assuming the role of a customer service representative of a clothing store. Our bot gives responses and provide actions based on the user input, by searching existence of certain keywords used in the user input and assign values to the state variable and progress based on the state. If a user gets to a point where the bot notices it cannot go any further (e.g. the action is fully completed) the bot reverts to the default state which asks the user whether they have other requests or questions. There are also basic information stored, such as opening hours, shop location and a list of generic clothing and accessories items. There are 4 actions the bot can artificially synthesize as interactions with the user: The bot can find certain items for the user and ask the user to either have it shipped or reserved. The bot can give shop location and office hours. The bot can cancel and track orders using randomizer to determine whether a user has an order or not and whether a package is delivered, on its way or not yet shipped. The bot can receive feedback from users as well.

This chatbot program has five classes: findstate, response, main, GUI and Stemmer.

public class main
    This class stores data, variables and dictionaries.
public class response
    This class is responsible for accessing the right response based on the current state of the conversation.
public class findstate
    This class creates the flow of the conversation by determining the state of the current situation.
public class GUI
    This class constructs the GUI client for the program and runs the main method. Launch GUI.java to start the program.
public class Stemmer
    This class implements Porter Stemmer for basic spell checking. It can detect reasonable typos (one extra word, grammatical, etc.) but cannot detect larger errors. Source: https://tartarus.org/martin/PorterStemmer/java.txt

Changelog: GUI class, Dictionary for Accessories, Stemmer class (for spell check), Imported JWI library (for synonym check).


Features: 	Created a basic GUI for conversation between the bot and user.
		        (Makes the experience more user-friendly.)
            Added an extra topic (Accessories) for the bot to respond to.
		        (The bot can generate more responses.)
		        Has multiple responses to one input (chosen by random).
		        (The bot will not repeat a sentence like a recorder, improving the experience in general.)
		        Handles basic spelling error using Porter Stemmer.
		        (Increases room for error for users.)
		        Using WordNet to read synonyms.
		        (The bot can respond to similar words and doesn’t need to find specific words.)
