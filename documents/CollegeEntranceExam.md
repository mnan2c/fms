1. 出题，改卷，通知答题人。
2. 答题，提交，通知阅卷。
3. 统计分数。
4. 真题分数统计。


表：
1. 试题Tests：id, type(单选、多选、填空、简答), nameToEdit(用于试题编辑), nameToDisplay(用于试题展示), testPaperId(试题属于哪张试卷), sort（试题排序，选择序号的时候，提示当前最大序号是多少），answer(试题答案), userAnswer, createdDate, points

2. 试卷表TestPaper：id, name, createdDate, totalPoints（总得分），createdBy（出题人），
	eachPartPoints(各模块得分)，startTime开始答题时间，endTime结束答题时间,	comment（评价）
	
	
## 说明
