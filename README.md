# Text-steganography
Steganography tool that allow to embed text inside chosen images. The main purpose for developing of this project is for learn Java.Swing.
The steganography process is implemented using the ImageJ processor

# How to use
1. Download and install Fiji from http://fiji.sc/Downloads
	wget -c http://downloads.imagej.net/fiji/latest/fiji-linux64.zip
	sudo mv ./fiji-linux64.zip /opt
	cd /opt
	sudo unzip ./fiji-linux64.zip
	sudo rm ./fiji-linux64.zip
2. Put the project folder into plugin directory
  	- cd /path-to-fiji/plugins
  	- sudo git clone https://github.com/seppho/text-steganography
3. Open the Fiji directory root
	- cd /path-to/fiji
4. Compile the plugin
	- ./ImageJ-linux64 --javac -d plugins/text-steganography/ plugins/text-steganography/*.java
3. Exec Fiji

4. Open a image file from Fiji

5. Select Steganography from plugin menu
