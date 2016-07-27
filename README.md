# Mood Based Chat Application
##Introduction

The objective of this project is to predict the mood of a text conversation. In this project, we
design a chat application that would change the background of the chat based on the mood of
the conversation. The moods detected would be happy (positive), sad (negative) and neutral.
The application will use a supervised machine learning algorithm to extract sentiments from a
series of text conversations.

##Dataset

This application uses the Sentiment Labelled Sentences Dataset that contains sentiment labelled reviews from Amazon, Yelp and IMDB

##Execution

The application was run and tested on Eclipse Mars

Pre-Requisites:
1.weka.jar
2.JRE 1.8
3.jgoodies-forms-1.8.0.jar

Steps to Execute in windows:

1. Create a workspace and load the source files in the workspace by importing MBChatAppSource
2. Give the path where amazon_long_filtered.arff file is stored. Ex:"C:\\Users\\Documents\\MLProject\\amazon_long_filtered.arff". 
3. Run StartWeka.java by pressing the run button
4. chat_client and chat_server window opens.
