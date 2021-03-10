import random

def generateRandomLine(sections, days, timesMWF, timesTR, periods):
    MWF = [True, False]
    randomLine = ""

    section = random.choice(sections)
    day = random.choice(days)

    threeDays = random.choice(MWF)
    if threeDays:
        time = random.choice(timesMWF)
    else:
        time = random.choice(timesTR)

    period = random.choice(periods)

    randomLine = """@%s@%s@%s@%s""" % (section, day, time, period)
    return randomLine


def generateNewLines(filename):
    lines = []
    with open(inputfilename, 'r') as file:
        for line in file:
            line = line.strip()

            if line.find('$') != -1:
                lines.append(line)
            else:
                extraLine = generateRandomLine(sections, days, timesMWF, timesTR, periods)
                newLine = line + extraLine
                lines.append(newLine)
    return lines


def generateSQLStatements(lines, tablename):
    statements = []

    for line in lines:
        separated_lines = line.split("@")

        if len(separated_lines) == 1:
            continue

        # id           = separated_lines[0] -> str
        # name         = separated_lines[1] -> str
        # credit_hours = separated_lines[2] -> int
        # section      = separated_lines[3] -> str
        # days         = separated_lines[4] -> str
        # time         = separated_lines[5] -> hour:minute-hour:minute
        # period       = separated_lines[6] -> month:day-month:day

        statement = '''INSERT INTO %s (id, name, credit_hours, section, days, time, period) VALUES (\'%s\', \'%s\', %s, \'%s\', \'%s\', \'%s\', \'%s\');''' % (
            tablename, separated_lines[0], 
            separated_lines[1], separated_lines[2], 
            separated_lines[3], separated_lines[4], 
            separated_lines[5], separated_lines[6]
        )
        statements.append(statement)

    return statements


sections = ["A01"]
days = ["MWF","TR"]
timesMWF = [
    "09:30-10:20",
    "10:30-11:20",
    "11:30-12:20",
    "12:30-13:20",
    "14:30-15:20"
    ]
timesTR = [
    "09:30-10:20",
    "10:30-11:20",
    "11:30-12:20",
    "12:30-13:20",
    "14:30-15:20"
    ]
periods = ["2021/01/18-2021/04/18"]

inputfilename = 'classdatabase.txt'
tablename = "courses"

newLines = generateNewLines(inputfilename)
statements = generateSQLStatements(newLines, tablename)

for s in statements:
    print(s)

