//
//  ViewController.m
//  MessageTweet
//
//  Created by iem on 11/12/2014.
//  Copyright (c) 2014 iem. All rights reserved.
//

#import "ViewController.h"

@interface ViewController (){
    NSArray *arrayWord;
    NSArray *arrayEmoji;
}

@property (weak, nonatomic) IBOutlet UITextField *text;
@property (weak, nonatomic) IBOutlet UIPickerView *picker;
@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    arrayWord = @[@"dors", @"mange", @"suis en cours", @"galère", @"cours", @"poireaute"];
    arrayEmoji = @[@";)", @":)", @":(", @":O", @"8)", @":o", @":D", @"mdr", @"lol"];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)backgroundTap:(id)esnder {
    [self dismissKeyBoard];
}

- (void)dismissKeyBoard{
    [[self view] endEditing:YES];
}

// returns the number of 'columns' to display.
- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView{
    return 2;
}

// returns the # of rows in each component..
- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component{
    if (component == 0) {
        return [arrayWord count];
    } else if(component == 1){
        return [arrayEmoji count];
    }
    
    return 0;
}

- (NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component{
    if (component == 0) {
        return [arrayWord objectAtIndex:row];
    } else if(component == 1){
        return [arrayEmoji objectAtIndex:row];
    }
    
    return @"";
}

- (IBAction)tweetIt:(id)sender {
    NSString *strText = [[self text] text];
    NSString *strPickerText = [arrayWord objectAtIndex:[[self picker] selectedRowInComponent:0]];
    NSString *strPickerEmoji = [arrayEmoji objectAtIndex:[[self picker] selectedRowInComponent:1]];
    NSLog(@"%@ Je %@ %@", strText, strPickerText, strPickerEmoji);
}
@end
