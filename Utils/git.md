# Git Utils

    git reset --hard HEAD~1

    git push origin +main --force
    git pull origin master

    git switch -f develop

    git rm --cached -r .idea

    git rebase --abort

    git submodule add [url] [folder]
    git submodule update --init --recursive
    git submodule deinit --all
    git submodule update --rebase
    git submodule status --recursive
    git submodule sync

## Examples

git clone <https://github.pactual.net/bankingmobile/request-card-android.git> features/card_offers
git submodule add -b main -f <https://github.pactual.net/bankingmobile/request-card-android.git> features/card_offers

[submodule "features/card_offers"]
    path = features/card_offers
    url = <https://github.pactual.net/bankingmobile/request-card-android.git>
    ignore = all
    branch = main
