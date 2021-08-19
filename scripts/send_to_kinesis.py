'''
Python Kinesis Producer

usage: send_to_kinesis.py [-h] [-p POOL_SIZE] [-s SESSIONS_DAY]
                          [-d DURATION_SECONDS] [-o OUTPUT_EVENTS]

optional arguments:
  -h, --help            show this help message and exit
  -p POOL_SIZE, --pool_size POOL_SIZE
                        The user_pool_size used to simulate the events
  -s SESSIONS_DAY, --sessions_day SESSIONS_DAY
                        The sessions_per_day used to simulate the events
  -d DURATION_SECONDS, --duration_seconds DURATION_SECONDS
                        The duration_seconds of the simulation
  -o OUTPUT_EVENTS, --output_events OUTPUT_EVENTS
                        Output events in the shell or not (0 - False, Any
                        integer - True)
'''


import boto3
import json
from fake_web_events import Simulation
import argparse
import logging
import sys

logging.basicConfig(stream=sys.stdout)
logger = logging.getLogger('send_to_kinesis_script')
logger.setLevel(logging.DEBUG)

client = boto3.Session(profile_name='terraform').client('firehose', region_name='us-east-1')
 

# sends directly to firehose
def put_record(event,output_events):
    data = (json.dumps(event) + '\n').encode('utf-8') 
    response = client.put_record(
        DeliveryStreamName='cwoche-kinesis-firehose-s3-stream',
        Record = {
            'Data': data
        }
    )
    
    if bool(output_events): print(event)
    return response



if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Python Kinesis Producer')
    parser.add_argument("-p", "--pool_size",
                        help="The user_pool_size used to simulate the events",
                        default=100,
                        type=int)

    parser.add_argument("-s", "--sessions_day",
                        help="The sessions_per_day used to simulate the events",
                        default=1000,
                        type=int)

    parser.add_argument("-d", "--duration_seconds",
                        help="The duration_seconds of the simulation",
                        default=300,
                        type=int)

    parser.add_argument("-o", "--output_events",
                        help="Output events in the shell or not (0 - False, Any integer - True)",
                        default=1,
                        type=int)

    args = parser.parse_args()



    simulation = Simulation(user_pool_size=args.pool_size,
                            sessions_per_day=args.sessions_day)

    logger.info("Starting simulation")
    events = simulation.run(duration_seconds=args.duration_seconds)

    logger.info("Sending events")
    for event in events:
        put_record(event,args.output_events)

