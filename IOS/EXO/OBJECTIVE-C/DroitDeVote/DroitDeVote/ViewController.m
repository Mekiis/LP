//
//  ViewController.m
//  DroitDeVote
//
//  Created by iem on 04/12/2014.
//  Copyright (c) 2014 iem. All rights reserved.
//

#import "ViewController.h"
#import "Person.h"

@interface ViewController (){
    Person *p;
}
@property (weak, nonatomic) IBOutlet UILabel *ageLabel;
@property (weak, nonatomic) IBOutlet UILabel *droitDeVoteLabel;

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    p = [Person createPersonWithAge:1];
    [self displayAge];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
- (IBAction)changeAge:(id)sender {
    UISlider *slider = (UISlider *)sender;
    [p setAge:[slider value]];
    [self displayAge];
}

- (void)displayAge{
    NSString *yearStr = [[NSString alloc] init];
    if([p age] == 1)
        yearStr = @"year";
    else
        yearStr = @"years";
    [_ageLabel setText:[NSString stringWithFormat:@"%d %@", [p age], yearStr]];
    
    if([p canLegallyVote]){
        [_droitDeVoteLabel setText:@"Yes"];
    } else {
        [_droitDeVoteLabel setText:@"No"];
    }
}

- (IBAction)mryPeople:(id)sender {
    Person *p1 = [Person createPersonWithAge:1];
    Person *p2 = [Person createPersonWithAge:1];
    [p1 setSpouse:p2];
    [p2 setSpouse:p1];
}

@end
