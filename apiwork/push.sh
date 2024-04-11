#!/bin/bash
rsync ./ gaia@145.24.223.74:~/api -r
echo 'pushed project to server'