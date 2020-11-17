#!/bin/bash
#
#require sudo
if [[ $SUDO_UID != 1000 ]]; then
    echo "Please run this script with sudo:"
    echo "sudo $0 $*"
    exit 1
fi

if [ -d "crypto-price-bot" ]; then
    rm -Rfv crypto-price-bot
fi

git clone https://github.com/irhiggs/crypto-price-bot
cd crypto-price-bot
git checkout `git tag --sort=v:refname | tail -n 1`
chmod +x ./gradlew
./gradlew clean assemble
mv `find build/libs/*.jar` /var/lib/crypto-bot/crypto-price-bot.jar

if [ ! -d "/var/lib/crypto-bot" ]; then
    mkdir -p /var/lib/crypto-bot
fi
ln -fs /var/lib/crypto-bot/crypto-price-bot.jar /etc/init.d/crypto-bot
chmod +x /etc/init.d/crypto-bot

if [ ! -d "/etc/systemd/system/crypto-bot.service.d" ]; then
    mkdir -p /etc/systemd/system/crypto-bot.service.d
fi
echo "[Service]" > /etc/systemd/system/crypto-bot.service.d/override.conf
echo "Environment=\"server_port=8081\"" >> /etc/systemd/system/crypto-bot.service.d/override.conf
echo "Environment=\"com_isaacray_cryptoPriceBot_botToken=YouNeedYourOwnDiscordToken\"" >> /etc/systemd/system/crypto-bot.service.d/override.conf
systemctl daemon-reload

service crypto-bot restart
cd ..
rm -Rf crypto-price-bot

update-rc.d crypto-bot defaults
