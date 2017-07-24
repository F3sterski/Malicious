######################################
# README                             #
# Malicious 1.0                      #
######################################

--------------------------------------
INSTALLATION                         |
--------------------------------------
1. Run Netbeans IDE then choose tab Tools -> Plugins.
2. Choose Add Plugins and enter installation file location(malicious-module.nbm).
3. After choice the proper installation file - module should be added as available plugin. Check Malicous Module on the list and choose Install.
4. In the next step we choose Next, and then we should accept license and choose Install.
5. Module is unsigned, so we must choose Continue in warning frame.
6. After installation we click Finish and copy configuration file (malicious.cfg) to Netbeans installation directory.
7. The last step is to restart Netbeans IDE.
--------------------------------------
CONFIGURATION USAGE                  |
--------------------------------------
Malicious module can be configured by configuration file named malicious.cfg.
Correct name of each malice to run should be listed in a separate line in this file.
--------------------------------------
LIST OF MALICE NAMES WITH DESCRIPTIONS
clipboard: disabling clipboard
key_malice: manipulating the content of currently open file (additional chars, multiple deleting chars), fake unsuccessful build, closing output panel
environment: freezing IDE
vanish_window: vanishing window
hide_window: hiding window
minimalize_window: minimalizing window
delete_semicolons: deleting all semicolons in currently open file
mouse_malice: manipulating caret position and selected text in the editor, docking editor pane into Navigator, Explorer and Output Panels on double click
--------------------------------------
Entering only a name causes the execution of malice with default parameters.
In order to customize malice execution specfic parameters should be written in the form:
malice_name -parameter_name parameter_value
--------------------------------------
LIST OF MALICE NAMES WITH PARAMETERS
clipboard -single time
key_malice -single time -frequency range(a-b)
environment -repeated time -length time
vanish_window -repeated time
hide_window -repeated time
minimalize_window -repeated time
delete_semicolons -single time
mouse_malice -single time -frequency range(a-b)
-------------------------------------
Each malice can be run once (-single) or frequently (-repeated).
Time is given in format 'X' or 'Xs' where X,Y are numbers and the unit is second. It can be also definied in minutes 'Xmin' or minutes and seconds 'XminYs'. Time can be also given as range.
Range is given in format 'A-B' where A,B are numbers. Default unit for time parameter is second ('A-Bs' as well), but it can be also given in minutes 'A-Bmin'.
-------------------------------------
Comment lines in configuration file starting with # character.
-------------------------------------
LOGGER CONFIGURATION
Log file path is defined in configuration file. To change log location, just replace one of path:
malicious_log - main application log that contains all information about invoking malicious
error_log - additional log that contains information about unexpected errors