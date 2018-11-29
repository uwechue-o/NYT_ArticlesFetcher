
===========
DESCRIPTION
===========
Basic app that accesses the New York Times Articles Search API.

Retrieves and displays a list of articles and thumbnail images related to a user-supplied search term.
When user selects a particular article from the resultset, it fetches and displays further details about that article.

Uses Jetpack Navigation Component architecture, Jetpack DataBinding, and RxJava/Retrofit for generating and processing
streams of observable data.

Utilizes the Single-Activity architecture.
All screens are simply fragments tied together via the navigation infrastructure.

==========
SETUP
==========
You will need to install the Android NDK, "CMake", and "Ninja" C/C++ compiler tools.
(I use the native layer to hide sensitive API keys.)

Add the CMake and Ninja bin folders to your system PATH.
Update your Gradle "local.properties" file with the correct path URIs for the NDK and CMake.
