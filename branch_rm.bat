ECHO "============================= RELEASE remove START..."

:: 版本号信息(需要手动指定)
SET oldVersion=1.0.4
SET projectName=junitperf


:: execute
SET oldBranchName=release_%oldVersion%
git branch -d %oldBranchName%
git push origin --delete %oldBranchName%

echo "1. Branch remove success..."

